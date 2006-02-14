package org.drools.ide.editors.completion;

import java.util.ArrayList;
import java.util.List;

import org.drools.ide.editors.Keywords;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;


/**
 * This is the basic completion processor that is used when the editor is outside of a rule block
 * partition.
 * The provides the content assistance for basic rule assembly stuff.
 * 
 * Subclasses enhance this when a user is actually editing a rule.
 * 
 * This processor will also read behind the current editing position, to provide some context to 
 * help provide thepop up list.
 * 
 * @author Michael Neale
 */
public class DefaultCompletionProcessor implements IContentAssistProcessor {

	

	/**
     * This will filter based on what was typed in previously.
	 */
	public ICompletionProposal[] computeCompletionProposals(
		ITextViewer viewer,
		int documentOffset) {

//        partitionDebug( viewer,
//                        documentOffset );

        Offset offset = new Offset(documentOffset);
        
        
        try {
            IDocument doc = viewer.getDocument();

            
            String backText = readBackwards( offset,
                                           doc );            
            
            List props = getPossibleProposals(viewer, backText);    
            props = filterProposals( offset,
                                     props,
                                     backText );
            
            ICompletionProposal[] result = new ICompletionProposal[props.size()];
            for (int i = 0; i < props.size(); i++) {
                result[i] = makeProposal( documentOffset,
                              offset,
                              props,
                              i );
            }        
            return result;
            
        } catch (BadLocationException e) {
        }
        
        return null;
		
	}


//    /**
//     * Just used for debugging, when it all gets to much.
//     */
//    private void partitionDebug(ITextViewer viewer,
//                                int documentOffset) {
//        try {
//            ITypedRegion region = viewer.getDocument().getPartition(documentOffset);
//            System.out.println("Offset: " + documentOffset);
//            System.out.println("Region offset: " + region.getOffset());
//            System.out.println("Region length: " + region.getLength());
//            
//            
//        }
//        catch ( BadLocationException e1 ) {
//            
//            e1.printStackTrace();
//        }
//    }


    protected CompletionProposal makeProposal(int documentOffset,
                                              Offset offset,
                                              List props,
                                              int i) {
        String replacement = (String) props.get(i);
        return new CompletionProposal(replacement, 
        				documentOffset - offset.getPrefixLength(), offset.getPrefixLength(), replacement.length());
    }


    /** Subclasses *should* override this to change the list. */
    protected List getPossibleProposals(ITextViewer viewer, String backText) {
        Keywords keys = Keywords.getInstance();
        List list = new ArrayList();
        list.add(keys.lookup("rule"));
        list.add(keys.lookup("import"));
        list.add(keys.lookup("expander"));
        return list;
    }


    /** Filter the proposals based on what was typed. */
    private List filterProposals(Offset offset,
                                     List props,
                                     String prefix) throws BadLocationException {

        if (doesPrefixExist(prefix)) {
            prefix = stripWhiteSpace(prefix);
            offset.prefix = prefix;
            props = filterList(props, prefix);
        }
        return props;
    }


    /**
     * Read some text from behind the cursor position.
     * This provides context to both filter what is shown based
     * on what the user has typed in, and also to provide more information for the 
     * list of suggestions based on context.
     */
    private String readBackwards(Offset offset,
                                 IDocument doc) throws BadLocationException {
        int startPart = doc.getPartition(offset.documentOffset).getOffset();
        if (startPart == 0) {
            if (offset.documentOffset < 32) {
                startPart = 0;
            }
            startPart = offset.documentOffset - 32;
        }
        
        String prefix = doc.get(startPart, 
                                offset.documentOffset - startPart);
        return prefix;
    }

    
    /** Filter the list by prefix */
    List filterList(List list, String prefix) {
        
        List result = new ArrayList();
        for ( int i = 0; i < list.size(); i++ ) {
            String item = (String) list.get(i);
            if (item.startsWith(prefix)) {
                result.add(item);
            }
        }
        return result;
    }

    boolean doesPrefixExist(String s) {
        if (s.length() == 0) return false;
        return !(s.charAt(s.length() - 1) == ' ');
    }
    
    /** Looks behind, gets stuff after the white space. Basically ripping out the last word.*/
    String stripWhiteSpace(String prefix) {
        
        char[] c = prefix.toCharArray();

        int start = 0;
        for (int i = c.length - 1; i >=0; i-- ) {
            if (Character.isWhitespace(c[i])) {
                start = i + 1;
                break;
            }
        }
      
        prefix = prefix.substring(start, prefix.length());
        return prefix;        
    }
    
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	// For Context information 
	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public IContextInformation[] computeContextInformation(
		ITextViewer viewer,
		int documentOffset) {
		return null;
	}

	/* (non-Javadoc)
	 * Method declared on IContentAssistProcessor
	 */
	public String getErrorMessage() {
		return null;
	}
    
    
    /** used to track where to drop in replacement stuff */
    static class Offset {
        
        public int documentOffset; 
        public String prefix;
        public Offset(int documentOffset) {
            this.documentOffset = documentOffset;
        }
        
        
        public int getPrefixLength() {
            if (prefix == null) return 0;
            return prefix.length();
        }
    }
}
