package com.dan.almedici.enrolamiento.utils;

public class Validator {
    //Expresiones de la  libreria de validación por 
    /// <summary>
    /// URL regular expresion for URL
    /// </summary>
    public static final String URL = "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\?=.-]*)*\\/?$/";
    /// <summary>
    ///  eMail regular expresion for  email data
    /// </summary>
    public static final String EMAIL = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    /// <summary>
    /// Telefono  regular expresion for phone numbers in Mexico
    /// </summary>
    public static final String TELEFONO = "^[+-]?\\d+(\\.\\d+)?$";
/// <summary>
    /// Nombres regular expresion for   First Name and Last Name
    /// </summary>
    public static final String NOMBRES = "^[A-Za-z ]+$";
    /// <summary>
    /// RFC  regular expresion for valid stricture RFC data
    /// </summary>
    //public const string RFC = "/^([A-Z,Ñ,&]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$/";
    public static final String RFC =   "^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$";
    /// <summary>
    /// CURP  regular expresion for CURP  data structure
    /// </summary>
    //public const string CURP = "^.*(?=.{18})(?=.*[0-9])(?=.*[A-ZÑ]).*$";
    public static final String CURP = "^([A-Z][A,E,I,O,U,X][A-Z]{2})(\\d{2})((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01])|02(0[1-9]|[12]\\d)|(04|06|09|11)(0[1-9]|[12]\\d|30))([M,H])(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)([B,C,D,F,G,H,J,K,L,M,N,Ñ,P,Q,R,S,T,V,W,X,Y,Z]{3})([0-9,A-Z][0-9])$";
    // password
    public static final String PASSWORD = "[^a-zA-z0-9 ]+";


}
