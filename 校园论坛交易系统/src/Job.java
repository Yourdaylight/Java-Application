import java.util.ArrayList;

public class Job extends Post {
    private double ProposedPrice;
    private double LowestOffer=999999999;

    public double getProposedPrice(){return this.ProposedPrice;}
    public double getLowestOffer(){return this.LowestOffer;}

    public void setProposedPrice(double proposedPrice){this.ProposedPrice=proposedPrice;}
    public void setLowestOffer(double lowestOffer){this.LowestOffer=lowestOffer;}

    Job(String Id, String Title, String Description, String CreatorID, double proposedPrice) {
        super(Id, Title, Description, CreatorID);
        this.ProposedPrice=proposedPrice;
    }

    public String getPostDetails(){
        String str=
                "ID:              "+getId()+"\n"+
                "Title:           "+getTitle()+"\n"+
                "Description:     "+getDescription()+"\n"+
                "Creator ID:      "+getCreatorID()+"\n"+
                "Status:          "+getStatus()+"\n"+
                "Proposed raise:  $"+getProposedPrice()+"\n"+
                "Lowest offer:    $";
        if(getLowestOffer()==999999999)
            str+="NO OFFER\n";
        else
            str+=getLowestOffer()+"\n";

        return str;
    }
    @Override
    public boolean handleReply(Reply reply) {
        if(reply.getValue()<this.getLowestOffer() && this.getStatus().equals("OPEN")){
            ArrayList<Reply> toAdd=getReplies();
            toAdd.add(reply);
            setReplies(toAdd);
            setLowestOffer(reply.getValue());
            if(reply.getValue()<=this.getProposedPrice())
                setStatus("CLOSED");
            return true;
        }
        return false;
    }

    @Override
    public String getReplyDetails() {
        String ids=getPostDetails();

        String history="-- Offer History --\n";
        for(Reply r:getReplies())
            history+=r.getReponderid()+": $"+r.getValue()+"\n";

        return ids+history;
    }
}
