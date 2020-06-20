public class Reply {
    private String Postid;
    private double Value;
    private String Reponderid;

    Reply(String postid,double value,String reponderid){
        this.Postid=postid;
        this.Value=value;
        this.Reponderid=reponderid;
    }

    public String getPostid(){return this.Postid;}
    public double getValue(){return  this.Value;}
    public String getReponderid(){return this.Reponderid;}

    public void setPostid(String postid){this.Postid=postid;}
    public void setValue(double value){this.Value=value;}
    public void setReponderid(String reponderid){this.Reponderid=reponderid;}


}
