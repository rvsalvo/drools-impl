package com.wordpress.salaboy.pachinkoo;


/**
 * @author ezsalro
 *
 */
public class FieldRestriction {

    private Class< ? > type;

    private String field;


    public FieldRestriction( Class< ? > type, String field ) {

        super();
        this.type = type;
        this.field = field;
    }


    public String getField() {

        return field;
    }


    public void setField( String field ) {

        this.field = field;
    }


    /**
     * @return the type
     */
    public Class< ? > getType() {

        return type;
    }


    /**
     * @param type
     *            the type to set
     */
    public void setType( Class< ? > type ) {

        this.type = type;
    }

}
