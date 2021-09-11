import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedList;
@JsonRootName("suggestions")
public class SuggestResponse {

    LinkedList<CompanyCard> companyCards;

    @JsonProperty("suggestions")
    @JsonGetter
    public LinkedList<CompanyCard> getCompanyCards() {
        return companyCards;
    }
    public void setCompanyCards(LinkedList<CompanyCard> cards){
        this.companyCards =cards;
    }

    public void PrintSuggests(){
        for (CompanyCard card: this.companyCards) {
            System.out.println(card+"\n");
        }
    }
}
