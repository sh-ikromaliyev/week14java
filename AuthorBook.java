package org.example;
public class AuthorBook {
    private String firstName,lastName,isbn,title;
    public AuthorBook(String f,String l,String i,String t){firstName=f;lastName=l;isbn=i;title=t;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getIsbn(){return isbn;}
    public String getTitle(){return title;}
}
