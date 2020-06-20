import java.util.ArrayList;

public abstract class Post {
    private String Id;
    private String Title;
    private String Description;
    private String CreatorID;
    private String Status;//Can only be either  OPEN or CLOSED
    private ArrayList<Reply> Replies;


    public String getId(){return this.Id;}
    public String getTitle(){return this.Title;}
    public String getDescription(){return this.Description;}
    public String getCreatorID(){return this.CreatorID;}
    public String getStatus(){return this.Status;}
    public ArrayList<Reply> getReplies(){return this.Replies;}

    public void setId(String id){this.Id=id;}
    public void setTitle(String title){this.Title=title;}
    public void setDescription(String description){this.Description=description;}
    public void setCreatorID(String creatorID){this.CreatorID=creatorID;}
    public void setStatus(String status){this.Status=status;}
    public void setReplies(ArrayList<Reply> replies){this.Replies=replies;}


    Post(String Id,String Title,String Description,String CreatorID)
    {
        this.Id=Id;
        this.Title=Title;
        this.Description=Description;
        this.CreatorID=CreatorID;
        this.Status="OPEN";
        this.Replies=new ArrayList<>();
    }

    public String getPostDetails(){
        String str= "ID:              "+this.Id+"\n"+
                    "Title:           "+this.Title+"\n"+
                    "Description:     "+this.Description+"\n"+
                    "Creator ID:      "+this.CreatorID+"\n"+
                    "Status:          "+this.Status+"\n";
        return str;
    }

    public abstract boolean handleReply(Reply reply);
    public abstract String getReplyDetails();
}
