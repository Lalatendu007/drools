package org.drools.task.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.drools.task.Attachment;
import org.drools.task.Comment;
import org.drools.task.Content;
import org.drools.task.Deadline;
import org.drools.task.Delegation;
import org.drools.task.Group;
import org.drools.task.OrganizationalEntity;
import org.drools.task.PeopleAssignments;
import org.drools.task.Status;
import org.drools.task.Task;
import org.drools.task.TaskData;
import org.drools.task.User;
import org.drools.task.query.DeadlineSummary;
import org.drools.task.query.TaskSummary;
import org.drools.task.service.TaskService.ScheduledTaskDeadline;

public class TaskServiceSession {
    TaskService   service;
    EntityManager em;

    public TaskServiceSession(TaskService service,
                              EntityManager em) {
        this.service = service;
        this.em = em;
    }

    public void dispose() {
        em.close();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void addUser(User user) {
        em.getTransaction().begin();
        em.persist( user );
        em.getTransaction().commit();
    }

    public void addGroup(Group group) {
        em.getTransaction().begin();
        em.persist( group );
        em.getTransaction().commit();
    }

    public void addTask(Task task) {
        TaskData taskData = task.getTaskData();
        // new tasks start off with status created
        taskData.setStatus( Status.Created );

        if ( task.getPeopleAssignments() != null ) {
            List potentialOwners = task.getPeopleAssignments().getPotentialOwners();
            if ( potentialOwners.size() == 1 ) {
                // if there is a single potential owner, assign and set status to Reserved
                taskData.setActualOwner( (User) potentialOwners.get( 0 ) );
                taskData.setStatus( Status.Reserved );
            } else if ( potentialOwners.size() > 1 ) {
                // multiple potential owners, so set to Ready so one can claim.
                taskData.setStatus( Status.Ready );
            } else {
                //@TODO we have no potential owners
            }
        } else {
            //@TODO we have no potential owners
        }

        // set the CreatedOn date if it's not already set
        if ( taskData.getCreatedOn() == null ) {
            taskData.setCreatedOn( new Date() );
        }

        //@FIXME for now we activate on creation, unless date is supplied
        if ( taskData.getActivationTime() == null ) {
            taskData.setActivationTime( taskData.getCreatedOn() );
        }

        em.getTransaction().begin();
        em.persist( task );
        em.getTransaction().commit();
        long now = System.currentTimeMillis();
        // schedule after it's been persisted, otherwise the id's won't be assigned
        if ( task.getDeadlines() != null ) {
            if ( task.getDeadlines().getStartDeadlines() != null ) {
                for ( Deadline deadline : task.getDeadlines().getStartDeadlines() ) {
                    if ( !deadline.isEscalated() ) {
                        // only escalate when true - typically this would only be true
                        // if the user is requested that the notification should never be escalated
                        Date date = deadline.getDate();
                        service.schedule( new ScheduledTaskDeadline( task.getId(),
                                                                     deadline.getId(),
                                                                     service ),
                                          date.getTime() - now );
                    }
                }
            }

            if ( task.getDeadlines().getEndDeadlines() != null ) {
                for ( Deadline deadline : task.getDeadlines().getEndDeadlines() ) {
                    // only escalate when true - typically this would only be true
                    // if the user is requested that the notification should never be escalated
                    if ( !deadline.isEscalated() ) {
                        Date date = deadline.getDate();
                        service.schedule( new ScheduledTaskDeadline( task.getId(),
                                                                     deadline.getId(),
                                                                     service ),
                                          date.getTime() - now );
                    }
                }
            }
        }

        if ( task.getTaskData().getStatus() == Status.Reserved ) {
            // Task was reserved so owner should get icals
            SendIcal.getInstance().sendIcalForTask( task,
                                                    service.getUserinfo() );

            // trigger event support
            service.getEventSupport().fireTaskClaimed( task.getId(),
                                                       task.getTaskData().getActualOwner().getId() );
        }
    }

    public void claim(long taskId,
                      String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );
        em.getTransaction().begin();
        claim(task, user);
        em.getTransaction().commit();
    }
    
    public void claim(Task task,
                      User user) {
        TaskData taskData = task.getTaskData();

        //task must be in status Ready
        if ( taskData.getStatus() == Status.Ready ) {
            // check permissions
            PeopleAssignments people = task.getPeopleAssignments();
            if ( isAllowed( user,
                            new List[]{people.getPotentialOwners(), people.getBusinessAdministrators()} ) ) {
                // only potential owners and business admin can claim a task
                taskData.setStatus( Status.Reserved );
                taskData.setActualOwner( user );

                // Task was reserved so owner should get icals
                SendIcal.getInstance().sendIcalForTask( task,
                                                        service.getUserinfo() );

                // trigger event support
                service.getEventSupport().fireTaskClaimed( task.getId(),
                                                           task.getTaskData().getActualOwner().getId() );
            } else {
                // @TODO Error
            }
        } else {
            // @TODO Error
        }
    }    

    public void start(long taskId,
                      String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        // Status must be Read or Reserved
        if ( taskData.getStatus() == Status.Ready ) {
            // if Ready must be potentialOwner
            PeopleAssignments people = task.getPeopleAssignments();
            if ( isAllowed( user,
                            new List[]{people.getPotentialOwners()} ) ) {
                em.getTransaction().begin();
                taskData.setActualOwner( user );
                taskData.setStatus( Status.InProgress );
                em.getTransaction().commit();
            } else {
                // @TODO Error
            }
        } else if ( taskData.getStatus() == Status.Reserved ) {
            // if Reserved must be actual owner
            if ( taskData.getActualOwner().getId().equals( user.getId() ) ) {
                em.getTransaction().begin();
                taskData.setStatus( Status.InProgress );
                em.getTransaction().commit();
            } else {
                // @TODO Error
            }
        } else {
            // @TODO Error
            return;
        }
    }

    public void stop(long taskId,
                     String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        PeopleAssignments people = task.getPeopleAssignments();
        if ( taskData.getStatus() == Status.InProgress && (taskData.getActualOwner().getId().equals( user.getId() ) || isAllowed( user,
                                                                                                                                  new List[]{people.getBusinessAdministrators()} )) ) {
            // Status must be InProgress and actual owner, switch to Reserved
            em.getTransaction().begin();
            taskData.setStatus( Status.Reserved );
            em.getTransaction().commit();
        } else {
            // @TODO Error
            return;
        }
    }

    public void release(long taskId,
                        String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        // task must be reserved or in progress and owned by user
        PeopleAssignments people = task.getPeopleAssignments();
        if ( (taskData.getStatus() == Status.Reserved || taskData.getStatus() == Status.InProgress) && (taskData.getActualOwner().getId().equals( user.getId() ) || isAllowed( user,
                                                                                                                                                                               new List[]{people.getBusinessAdministrators()} )) ) {
            em.getTransaction().begin();
            taskData.setStatus( Status.Ready );
            taskData.setActualOwner( null );
            em.getTransaction().commit();
        } else {
            //@TODO Error
        }
    }

    public void suspend(long taskId,
                        String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        List[] allowed;
        PeopleAssignments people = task.getPeopleAssignments();
        if ( taskData.getStatus() == Status.Ready ) {
            // If it's ready then potential owners can suspect too
            allowed = new List[]{people.getPotentialOwners(), people.getBusinessAdministrators()};
        } else {
            allowed = new List[]{people.getBusinessAdministrators()};
        }

        if ( (taskData.getStatus() == Status.Ready || taskData.getStatus() == Status.Reserved || taskData.getStatus() == Status.InProgress)
             && ((taskData.getActualOwner() != null && taskData.getActualOwner().getId().equals( user.getId() )) || isAllowed( user,
                                                                                                                               allowed )) ) {
            em.getTransaction().begin();
            taskData.setStatus( Status.Suspended );
            em.getTransaction().commit();
        } else {
            //@TODO Error            
        }
    }

    public void resume(long taskId,
                       String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        List[] allowed;
        PeopleAssignments people = task.getPeopleAssignments();
        if ( taskData.getPreviousStatus() == Status.Ready ) {
            // If it's ready then potential owners can suspect too
            allowed = new List[]{people.getPotentialOwners(), people.getBusinessAdministrators()};
        } else {
            allowed = new List[]{people.getBusinessAdministrators()};
        }

        if ( (taskData.getStatus() == Status.Suspended) && ((taskData.getActualOwner() != null && taskData.getActualOwner().getId().equals( user.getId() )) || isAllowed( user,
                                                                                                                                                                          allowed )) ) {
            em.getTransaction().begin();
            taskData.setStatus( taskData.getPreviousStatus() );
            em.getTransaction().commit();
        } else {
            //@TODO Error            
        }
    }

    public void skip(long taskId,
                     String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        List[] allowed;
        PeopleAssignments people = task.getPeopleAssignments();
        if ( taskData.getStatus() == Status.Ready ) {
            // If it's ready then potential owners can skip too
            allowed = new List[]{people.getPotentialOwners(), people.getBusinessAdministrators()};
        } else {
            allowed = new List[]{people.getBusinessAdministrators()};
        }

        if ( task.getTaskData().isSkipable() && (taskData.getStatus() != Status.Completed && taskData.getStatus() != Status.Failed)
             && ((taskData.getActualOwner() != null && taskData.getActualOwner().getId().equals( user.getId() )) || isAllowed( user,
                                                                                                                               allowed )) ) {
            em.getTransaction().begin();
            taskData.setStatus( Status.Obselete );
            em.getTransaction().commit();
        } else {
            //@TODO Error            
        }
    }

    public void complete(long taskId,
                         String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        if ( taskData.getStatus() == Status.InProgress && taskData.getActualOwner().getId().equals( user.getId() ) ) {
            // Status must be InProgress and actual owner, switch to Reserved
            em.getTransaction().begin();
            taskData.setStatus( Status.Completed );
            em.getTransaction().commit();
        } else {
            // @TODO Error
            return;
        }
    }

    public void delegate(long taskId,
                         String delegateId,
                         String userId) {
        Task task = em.find( Task.class,
                             taskId );
        TaskData taskData = task.getTaskData();
        Delegation delegation = task.getDelegation();
        
        
        User delegate = em.find( User.class,
                                   delegateId );
        
        User user = em.find( User.class,
                             userId );
        
        // check correct status
        if ( taskData.getStatus() == Status.Ready || taskData.getStatus() == Status.Reserved || taskData.getStatus() == Status.InProgress ) {
            // check valid user
            PeopleAssignments people = task.getPeopleAssignments();
            if ( taskData.getActualOwner().getId().equals( user.getId() ) || isAllowed( user,
                            new List[]{people.getPotentialOwners(), people.getBusinessAdministrators()} ) ) {
                // is valid delegate
               if ( isAllowed( delegate, new List[] { delegation.getDelegates() } ) ) {
                   em.getTransaction().begin();
                   taskData.setStatus( Status.Ready );
                   if ( people.getPotentialOwners().contains( delegate ) ) {
                       // Add delegate to potentialOwners if it's not already
                       people.getPotentialOwners().add( delegate );
                   }
                   claim(task, delegate);
                   em.getTransaction().commit();
               }
            } else {
                // @TODO ERROR
            }
        } else {
            // @TODO ERROR
        }
    }

    public void fail(long taskId,
                     String userId) {
        Task task = em.find( Task.class,
                             taskId );

        User user = em.find( User.class,
                             userId );

        TaskData taskData = task.getTaskData();

        if ( taskData.getStatus() == Status.InProgress && taskData.getActualOwner().getId().equals( user.getId() ) ) {
            // Status must be InProgress and actual owner, switch to Reserved
            em.getTransaction().begin();
            taskData.setStatus( Status.Failed );
            em.getTransaction().commit();
        } else {
            // @TODO Error
            return;
        }
    }

    public void addComment(long taskId,
                           Comment comment) {
        Task task = em.find( Task.class,
                             taskId );
        if ( task == null ) {
            // throw some exception
        }

        em.getTransaction().begin();

        List<Comment> list = task.getTaskData().getComments();
        if ( list == null || list == Collections.<Comment> emptyList() ) {
            list = new ArrayList<Comment>( 1 );
            task.getTaskData().setComments( list );
        }

        list.add( comment );

        em.getTransaction().commit();
    }

    public void addAttachment(long taskId,
                              Attachment attachment,
                              Content content) {
        Task task = em.find( Task.class,
                             taskId );

        if ( task == null ) {
            // throw some exception
        }

        em.getTransaction().begin();

        em.persist( content );
        attachment.setSize( content.getContent().length );
        attachment.setAttachmentContentId( content.getId() );

        List<Attachment> list = task.getTaskData().getAttachments();
        if ( list == null || list == Collections.<Attachment> emptyList() ) {
            list = new ArrayList<Attachment>( 1 );
            task.getTaskData().setAttachments( list );
        }

        list.add( attachment );
        em.getTransaction().commit();
    }

    public void setDocumentContent(long taskId,
                                   Content content) {
        Task task = em.find( Task.class,
                             taskId );

        if ( task == null ) {
            // throw some exception
        }

        em.getTransaction().begin();

        em.persist( content );

        task.getTaskData().setDocumentContentId( content.getId() );

        em.getTransaction().commit();
    }

    public Content getContent(long contentId) {
        Content content = em.find( Content.class,
                                   contentId );
        return content;
    }

    public void deleteAttachment(long taskId,
                                 long attachmentId,
                                 long contentId) {
        // @TODO I can't get this to work with HQL deleting the Attachment. Hibernate needs both the item removed from the collection
        // and also the item deleted, so for now have to load the entire Task, I suspect that this is due to using the same EM which 
        // is caching things.
        Task task = em.find( Task.class,
                             taskId );

        em.getTransaction().begin();
        for ( Iterator<Attachment> it = task.getTaskData().getAttachments().iterator(); it.hasNext(); ) {
            Attachment attachment = it.next();
            if ( attachment.getId() == attachmentId ) {
                it.remove();
                em.remove( attachment ); // need to do this otherwise it just removes the link id, without removing the attachment
                break;
            }
        }

        // we do this as HQL to avoid streaming in the entire HQL
        String deleteContent = "delete from Content where id = :id";
        em.createQuery( deleteContent ).setParameter( "id",
                                                      contentId ).executeUpdate();

        em.getTransaction().commit();
    }

    public void deleteComment(long taskId,
                              long commentId) {
        // @TODO I can't get this to work with HQL deleting the Comment. Hibernate needs both the item removed from the collection
        // and also the item deleted, so for now have to load the entire Task, I suspect that this is due to using the same EM which 
        // is caching things.
        Task task = em.find( Task.class,
                             taskId );
        em.getTransaction().begin();
        for ( Iterator<Comment> it = task.getTaskData().getComments().iterator(); it.hasNext(); ) {
            Comment comment = it.next();
            if ( comment.getId() == commentId ) {
                it.remove();
                em.remove( comment ); // need to do this otherwise it just removes the link id, without removing the comment
                break;
            }
        }
        em.getTransaction().commit();
    }

    public Task getTask(long taskId) {
        Task task = em.find( Task.class,
                             taskId );
        return task;
    }

    public List<DeadlineSummary> getUnescalatedDeadlines() {
        return (List<DeadlineSummary>) em.createNamedQuery( "UnescalatedDeadlines" ).getResultList();
    }

    public List<TaskSummary> getTasksOwned(String userId,
                                           String language) {
        Query tasksOwned = em.createNamedQuery( "TasksOwned" );
        tasksOwned.setParameter( "userId",
                                 userId );
        tasksOwned.setParameter( "language",
                                 language );
        List<TaskSummary> list = (List<TaskSummary>) tasksOwned.getResultList();
        return list;
    }

    public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(String userId,
                                                                     String language) {
        Query tasksAssignedAsBusinessAdministrator = em.createNamedQuery( "TasksAssignedAsBusinessAdministrator" );
        tasksAssignedAsBusinessAdministrator.setParameter( "userId",
                                                           userId );
        tasksAssignedAsBusinessAdministrator.setParameter( "language",
                                                           language );
        List<TaskSummary> list = (List<TaskSummary>) tasksAssignedAsBusinessAdministrator.getResultList();
        return list;
    }

    public List<TaskSummary> getTasksAssignedAsExcludedOwner(String userId,
                                                             String language) {
        Query tasksAssignedAsExcludedOwner = em.createNamedQuery( "TasksAssignedAsExcludedOwner" );
        tasksAssignedAsExcludedOwner.setParameter( "userId",
                                                   userId );
        tasksAssignedAsExcludedOwner.setParameter( "language",
                                                   language );
        List<TaskSummary> list = (List<TaskSummary>) tasksAssignedAsExcludedOwner.getResultList();
        return list;
    }

    public List<TaskSummary> getTasksAssignedAsPotentialOwner(String userId,
                                                              String language) {
        Query tasksAssignedAsPotentialOwner = em.createNamedQuery( "TasksAssignedAsPotentialOwner" );
        tasksAssignedAsPotentialOwner.setParameter( "userId",
                                                    userId );
        tasksAssignedAsPotentialOwner.setParameter( "language",
                                                    language );
        List<TaskSummary> list = (List<TaskSummary>) tasksAssignedAsPotentialOwner.getResultList();
        return list;
    }

    public List<TaskSummary> getTasksAssignedAsRecipient(String userId,
                                                         String language) {
        Query tasksAssignedAsRecipient = em.createNamedQuery( "TasksAssignedAsRecipient" );
        tasksAssignedAsRecipient.setParameter( "userId",
                                               userId );
        tasksAssignedAsRecipient.setParameter( "language",
                                               language );
        List<TaskSummary> list = (List<TaskSummary>) tasksAssignedAsRecipient.getResultList();
        return list;
    }

    public List<TaskSummary> getTasksAssignedAsTaskInitiator(String userId,
                                                             String language) {
        Query tasksAssignedAsTaskInitiator = em.createNamedQuery( "TasksAssignedAsTaskInitiator" );
        tasksAssignedAsTaskInitiator.setParameter( "userId",
                                                   userId );
        tasksAssignedAsTaskInitiator.setParameter( "language",
                                                   language );
        List<TaskSummary> list = (List<TaskSummary>) tasksAssignedAsTaskInitiator.getResultList();
        return list;
    }

    public List<TaskSummary> getTasksAssignedAsTaskStakeholder(String userId,
                                                               String language) {
        Query tasksAssignedAsTaskStakeholder = em.createNamedQuery( "TasksAssignedAsTaskStakeholder" );
        tasksAssignedAsTaskStakeholder.setParameter( "userId",
                                                     userId );
        tasksAssignedAsTaskStakeholder.setParameter( "language",
                                                     language );
        List<TaskSummary> list = (List<TaskSummary>) tasksAssignedAsTaskStakeholder.getResultList();
        return list;
    }

    public boolean isAllowed(User user,
                             List<OrganizationalEntity>[] people) {
        for ( List<OrganizationalEntity> list : people ) {
            if ( isAllowed( user,
                            list ) ) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllowed(User user,
                             List<OrganizationalEntity> entities) {
        // for now just do a contains, I'll figure out group membership later.
        for ( OrganizationalEntity entity : entities ) {
            if ( entity.getId().equals( user.getId() ) ) {
                return true;
            }
        }
        return false;
    }

    public static String toString(Reader reader) throws IOException {
        int charValue = 0;
        StringBuffer sb = new StringBuffer( 1024 );
        while ( (charValue = reader.read()) != -1 ) {
            //result = result + (char) charValue;
            sb.append( (char) charValue );
        }
        return sb.toString();
    }
}
