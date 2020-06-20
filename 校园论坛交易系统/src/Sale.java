import java.util.ArrayList;

/*
*  When a student views a sale post which is opened,
* he or she can only see the item name, description and the highest price offered at that point*/
public class Sale extends Post {
    private double AskingPrice;
    private double HigestOffer= 0;
    private double MinimumRaise;

    public double getAskingPrice(){return this.AskingPrice;}
    public double getHigestOffer(){return this.HigestOffer;}
    public double getMinimumRaise(){return this.MinimumRaise;}

    public void setAskingPrice(double askingPrice){this.AskingPrice=askingPrice;}
    public void setHigestOffer(double higestOffer){this.HigestOffer=higestOffer;}
    public void setMinimumRaise(double minimumRaise){this.MinimumRaise=minimumRaise;}


    Sale(String Id, String Title, String Description, String CreatorID,double askingPrice,double minimumRaise) {
        super(Id, Title, Description, CreatorID);
        this.AskingPrice=askingPrice;
        this.MinimumRaise=minimumRaise;
    }

    public String getPostDetails(){
        String str=
                "ID:              "+getId()+"\n"+
                "Title:           "+getTitle()+"\n"+
                "Description:     "+getDescription()+"\n"+
                "Creator ID:      "+getCreatorID()+"\n"+
                "Status:          "+getStatus()+"\n"+
                "Minimum raise:   $"+getMinimumRaise()+"\n"+
                "Highest offer:   $";
        if(getHigestOffer()==0)
            str+="NO OFFER\n";
        else
            str+=getHigestOffer()+"\n";

        return str;
    }
    @Override
    public boolean handleReply(Reply reply) {
        if(reply.getValue()>this.getHigestOffer() && this.getStatus().equals("OPEN")){
            ArrayList<Reply> toAdd=getReplies();
            toAdd.add(reply);
            setReplies(toAdd);
            setHigestOffer(reply.getValue());
            if(reply.getValue()>=this.getAskingPrice())
                setStatus("CLOSED");
            return true;
        }
        return false;
    }

    @Override
    public String getReplyDetails() {
        String ids=getPostDetails()+
                "Asking price:   "+getAskingPrice()+"\n";

        String history="-- Offer History --\n";
        for(Reply r:getReplies())
            history+=r.getReponderid()+": $"+r.getValue()+"\n";

        return ids+history;
    }
}
