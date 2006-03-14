package org.drools.base;

import java.util.Collection;

import org.drools.spi.Evaluator;

public class EvaluatorFactory {
    private static final EvaluatorFactory INSTANCE = new EvaluatorFactory();

    public static EvaluatorFactory getInstance() {
        return EvaluatorFactory.INSTANCE;
    }

    private EvaluatorFactory() {

    }
    
    public static Evaluator getEvaluator(int type, 
                                         String operator) {
        Evaluator evaluator = null;
        if ( operator.equals("==") ) {
            evaluator = getEvaluator(type, Evaluator.EQUAL);
        } else if ( operator.equals("!=") ) {
            evaluator = getEvaluator(type, Evaluator.NOT_EQUAL);
        } else if ( operator.equals("<") ) {
            evaluator = getEvaluator(type, Evaluator.LESS);
        } else if ( operator.equals("<=") ) {
            evaluator = getEvaluator(type, Evaluator.LESS_OR_EQUAL);
        } else if ( operator.equals(">") ) {
            evaluator = getEvaluator(type, Evaluator.GREATER);
        } else if ( operator.equals(">=") ) {
            evaluator = getEvaluator(type, Evaluator.GREATER_OR_EQUAL);
        } else if (operator.equals( "contains" ) ) {
            evaluator = getEvaluator(type, Evaluator.CONTAINS);
        } else {
            throw new IllegalArgumentException("Unknown operator: '" + operator + "'");
        }
        
        return evaluator;
    }

    public static Evaluator getEvaluator(int type,
                                         int operator) {
        switch ( type ) {
            case Evaluator.STRING_TYPE :
            case Evaluator.OBJECT_TYPE :
                return INSTANCE.getObjectEvaluator( operator );
            case Evaluator.SHORT_TYPE :
                return INSTANCE.getShortEvaluator( operator );
            case Evaluator.INTEGER_TYPE :
                return INSTANCE.getIntegerEvaluator( operator );
            case Evaluator.BOOLEAN_TYPE :
                return INSTANCE.getBooleanEvaluator( operator );  
            case Evaluator.DOUBLE_TYPE :
                return INSTANCE.getDoubleEvaluator( operator );
            default :
                throw new RuntimeException( "Type '" + type + "' does not exist for BaseEvaluatorFactory" );
        }
    }

    Evaluator getObjectEvaluator(int operator) {
        switch ( operator ) {
            case Evaluator.EQUAL :
                return ObjectEqualEvaluator.INSTANCE;
            case Evaluator.NOT_EQUAL :
                return ObjectNotEqualEvaluator.INSTANCE;
            case Evaluator.CONTAINS :
                return ObjectContainsEvaluator.INSTANCE;
            default :
                throw new RuntimeException( "Operator '" + operator + "' does not exist for ObjectEvaluator" );
        }
    }

    static class ObjectEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new ObjectEqualEvaluator();

        private ObjectEqualEvaluator() {
            super( Evaluator.OBJECT_TYPE,
                   Evaluator.EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return object1.equals( object2 );
        }
        
        public String toString() {
            return "Object ==";
        }
    }

    static class ObjectNotEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new ObjectNotEqualEvaluator();

        private ObjectNotEqualEvaluator() {
            super( Evaluator.OBJECT_TYPE,
                   Evaluator.NOT_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return !object1.equals( object2 );
        }
        
        public String toString() {
            return "Object !=";
        }        
    }
    
    static class ObjectContainsEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new ObjectContainsEvaluator();

        private ObjectContainsEvaluator() {
            super( Evaluator.OBJECT_TYPE,
                   Evaluator.CONTAINS );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            if (object2 == null) return false;
            
            //TODO: add support for hashes, normal arrays etc
            Collection col = (Collection) object2;
            return col.contains( object1 );
        }
        
        public String toString() {
            return "Object !=";
        }        
    }    
    
    Evaluator getBooleanEvaluator(int operator) {
        switch ( operator ) {
            case Evaluator.EQUAL :
                return BooleanEqualEvaluator.INSTANCE;
            case Evaluator.NOT_EQUAL :
                return BooleanNotEqualEvaluator.INSTANCE;
            default :
                throw new RuntimeException( "Operator '" + operator + "' does not exist for BooleanEvaluator" );
        }
    }      
    
    static class BooleanEqualEvaluator extends BaseEvaluator {
        private final static Evaluator INSTANCE = new BooleanEqualEvaluator();

        private BooleanEqualEvaluator() {
            super( Evaluator.BOOLEAN_TYPE,
                   Evaluator.EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Boolean)object1).booleanValue() == ((Boolean)object2).booleanValue();
        }
        
        public String toString() {
            return "Boolean ==";
        }        
    }      

    static class BooleanNotEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new BooleanNotEqualEvaluator();

        private BooleanNotEqualEvaluator() {
            super( Evaluator.BOOLEAN_TYPE,
                   Evaluator.NOT_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Boolean)object1).booleanValue() != ((Boolean)object2).booleanValue();
        }
        
        public String toString() {
            return "Boolean !=";
        }        
    }    

    Evaluator getShortEvaluator(int operator) {
        switch ( operator ) {
            case Evaluator.EQUAL :
                return ShortEqualEvaluator.getInstance();
            case Evaluator.NOT_EQUAL :
                return ShortNotEqualEvaluator.getInstance();
            case Evaluator.LESS :
                return ShortLessEvaluator.getInstance();
            case Evaluator.LESS_OR_EQUAL :
                return ShortLessOrEqualEvaluator.getInstance();
            case Evaluator.GREATER :
                return ShortGreaterEvaluator.getInstance();
            case Evaluator.GREATER_OR_EQUAL :
                return ShortGreaterOrEqualEvaluator.getInstance();
            default :
                throw new RuntimeException( "Operator '" + operator + "' does not exist for ShortEvaluator" );
        }
    }

    static class ShortEqualEvaluator extends BaseEvaluator {
        private static Evaluator INSTANCE;

        public static Evaluator getInstance() {
            if ( INSTANCE == null ) {
                INSTANCE = new ShortEqualEvaluator();
            }
            return INSTANCE;
        }

        private ShortEqualEvaluator() {
            super( Evaluator.SHORT_TYPE,
                   Evaluator.EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).shortValue() == ((Number) object2).shortValue();
        }
        
        public String toString() {
            return "Short ==";
        }         
    }

    static class ShortNotEqualEvaluator extends BaseEvaluator {
        private static Evaluator INSTANCE;

        public static Evaluator getInstance() {
            if ( INSTANCE == null ) {
                INSTANCE = new ShortNotEqualEvaluator();
            }
            return INSTANCE;
        }

        private ShortNotEqualEvaluator() {
            super( Evaluator.SHORT_TYPE,
                   Evaluator.NOT_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).shortValue() != ((Number) object2).shortValue();
        }
        
        public String toString() {
            return "Short !=";
        }         
    }

    static class ShortLessEvaluator extends BaseEvaluator {
        private static Evaluator INSTANCE;

        public static Evaluator getInstance() {
            if ( INSTANCE == null ) {
                INSTANCE = new ShortLessEvaluator();
            }
            return INSTANCE;
        }

        private ShortLessEvaluator() {
            super( Evaluator.SHORT_TYPE,
                   Evaluator.LESS );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).shortValue() < ((Number) object2).shortValue();
        }
        
        public String toString() {
            return "Short <";
        }         
    }

    static class ShortLessOrEqualEvaluator extends BaseEvaluator {
        private static Evaluator INSTANCE;

        public static Evaluator getInstance() {
            if ( INSTANCE == null ) {
                INSTANCE = new ShortLessOrEqualEvaluator();
            }
            return INSTANCE;
        }

        private ShortLessOrEqualEvaluator() {
            super( Evaluator.SHORT_TYPE,
                   Evaluator.LESS_OR_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).shortValue() <= ((Number) object2).shortValue();
        }
        
        public String toString() {
            return "Boolean <=";
        }         
    }

    static class ShortGreaterEvaluator extends BaseEvaluator {
        private static Evaluator INSTANCE;

        public static Evaluator getInstance() {
            if ( INSTANCE == null ) {
                INSTANCE = new ShortGreaterEvaluator();
            }
            return INSTANCE;
        }

        private ShortGreaterEvaluator() {
            super( Evaluator.SHORT_TYPE,
                   Evaluator.GREATER );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).shortValue() > ((Number) object2).shortValue();
        }
        
        public String toString() {
            return "Short >";
        }         
    }

    static class ShortGreaterOrEqualEvaluator extends BaseEvaluator {
        private static Evaluator INSTANCE;

        public static Evaluator getInstance() {
            if ( INSTANCE == null ) {
                INSTANCE = new ShortGreaterOrEqualEvaluator();
            }
            return INSTANCE;
        }

        private ShortGreaterOrEqualEvaluator() {
            super( Evaluator.SHORT_TYPE,
                   Evaluator.GREATER_OR_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).shortValue() >= ((Number) object2).shortValue();
        }
        
        public String toString() {
            return "Short >=";
        }         
    }

    Evaluator getIntegerEvaluator(int operator) {
        switch ( operator ) {
            case Evaluator.EQUAL :
                return IntegerEqualEvaluator.INSTANCE;
            case Evaluator.NOT_EQUAL :
                return IntegerNotEqualEvaluator.INSTANCE;
            case Evaluator.LESS :
                return IntegerLessEvaluator.INSTANCE;
            case Evaluator.LESS_OR_EQUAL :
                return IntegerLessOrEqualEvaluator.INSTANCE;
            case Evaluator.GREATER :
                return IntegerGreaterEvaluator.INSTANCE;
            case Evaluator.GREATER_OR_EQUAL :
                return IntegerGreaterOrEqualEvaluator.INSTANCE;
            default :
                throw new RuntimeException( "Operator '" + operator + "' does not exist for IntegerEvaluator" );
        }
    }

    
    
    
    static class IntegerEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new IntegerEqualEvaluator();

        private IntegerEqualEvaluator() {
            super( Evaluator.INTEGER_TYPE,
                   Evaluator.EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).intValue() == ((Number) object2).intValue();
        }
        
        public String toString() {
            return "Integer ==";
        }         
    }

    static class IntegerNotEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new IntegerNotEqualEvaluator();

        private IntegerNotEqualEvaluator() {
            super( Evaluator.INTEGER_TYPE,
                   Evaluator.NOT_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).intValue() != ((Number) object2).intValue();
        }
        
        public String toString() {
            return "Integer !=";
        }                 
    }

    static class IntegerLessEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new IntegerLessEvaluator();

        private IntegerLessEvaluator() {
            super( Evaluator.INTEGER_TYPE,
                   Evaluator.LESS );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).intValue() < ((Number) object2).intValue();
        }
        
        public String toString() {
            return "Integer <";
        }                 
    }

    static class IntegerLessOrEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new IntegerLessOrEqualEvaluator();

        private IntegerLessOrEqualEvaluator() {
            super( Evaluator.INTEGER_TYPE,
                   Evaluator.LESS_OR_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).intValue() <= ((Number) object2).intValue();
        }
        
        public String toString() {
            return "Integer <=";
        }         
    }

    static class IntegerGreaterEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new IntegerGreaterEvaluator();

        private IntegerGreaterEvaluator() {
            super( Evaluator.INTEGER_TYPE,
                   Evaluator.GREATER );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).intValue() > ((Number) object2).intValue();
        }
        
        public String toString() {
            return "Integer >";
        }         
    }

    static class IntegerGreaterOrEqualEvaluator extends BaseEvaluator {
        private final static Evaluator INSTANCE = new IntegerGreaterOrEqualEvaluator();

        private IntegerGreaterOrEqualEvaluator() {
            super( Evaluator.INTEGER_TYPE,
                   Evaluator.GREATER_OR_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).intValue() >= ((Number) object2).intValue();
        }
        
        public String toString() {
            return "Integer >=";
        }         
    }
    
    
    Evaluator getDoubleEvaluator(int operator) {
        switch ( operator ) {
            case Evaluator.EQUAL :
                return DoubleEqualEvaluator.INSTANCE;
            case Evaluator.NOT_EQUAL :
                return DoubleNotEqualEvaluator.INSTANCE;
            case Evaluator.LESS :
                return DoubleLessEvaluator.INSTANCE;
            case Evaluator.LESS_OR_EQUAL :
                return DoubleLessOrEqualEvaluator.INSTANCE;
            case Evaluator.GREATER :
                return DoubleGreaterEvaluator.INSTANCE;
            case Evaluator.GREATER_OR_EQUAL :
                return DoubleGreaterOrEqualEvaluator.INSTANCE;
            default :
                throw new RuntimeException( "Operator '" + operator + "' does not exist for DoubleEvaluator" );
        }
    }    
    
    
    static class DoubleEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new DoubleEqualEvaluator();

        private DoubleEqualEvaluator() {
            super( Evaluator.DOUBLE_TYPE,
                   Evaluator.EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).doubleValue() == ((Number) object2).doubleValue();
        }
        
        public String toString() {
            return "Double ==";
        }         
    }

    static class DoubleNotEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new DoubleNotEqualEvaluator();

        private DoubleNotEqualEvaluator() {
            super( Evaluator.DOUBLE_TYPE,
                   Evaluator.NOT_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).doubleValue() != ((Number) object2).doubleValue();
        }
        
        public String toString() {
            return "Double !=";
        }                 
    }

    static class DoubleLessEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new DoubleLessEvaluator();

        private DoubleLessEvaluator() {
            super( Evaluator.DOUBLE_TYPE,
                   Evaluator.LESS );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).doubleValue() < ((Number) object2).doubleValue();
        }
        
        public String toString() {
            return "Double <";
        }                 
    }

    static class DoubleLessOrEqualEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new DoubleLessOrEqualEvaluator();

        private DoubleLessOrEqualEvaluator() {
            super( Evaluator.DOUBLE_TYPE,
                   Evaluator.LESS_OR_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).doubleValue() <= ((Number) object2).doubleValue();
        }
        
        public String toString() {
            return "Double <=";
        }         
    }

    static class DoubleGreaterEvaluator extends BaseEvaluator {
        public final static Evaluator INSTANCE = new DoubleGreaterEvaluator();

        private DoubleGreaterEvaluator() {
            super( Evaluator.DOUBLE_TYPE,
                   Evaluator.GREATER );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).doubleValue() > ((Number) object2).doubleValue();
        }
        
        public String toString() {
            return "Double >";
        }         
    }

    static class DoubleGreaterOrEqualEvaluator extends BaseEvaluator {
        private final static Evaluator INSTANCE = new DoubleGreaterOrEqualEvaluator();

        private DoubleGreaterOrEqualEvaluator() {
            super( Evaluator.DOUBLE_TYPE,
                   Evaluator.GREATER_OR_EQUAL );
        }

        public boolean evaluate(Object object1,
                                Object object2) {
            return ((Number) object1).doubleValue() >= ((Number) object2).doubleValue();
        }
        
        public String toString() {
            return "Double >=";
        }         
    }    
    
    
}
