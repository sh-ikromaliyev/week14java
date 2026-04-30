package org.example;
public class Author {
    private int authorID;
    private String firstName;
    private String lastName;
    public Author(int id,String f,String l){authorID=id;firstName=f;lastName=l;}
    public int getAuthorID(){return authorID;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
}
