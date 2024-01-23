package ie.williamswalsh.intro.domain.composite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// This author has an embedded id.
// NameId class is embedded in containing class.
// NameId is the field used as an Identity of the class.
@Entity
@Table(name = "author_composite")   // uses same table as composite key firstName+lastName
public class AuthorEmbedded {

//    States that this class is the id of the containing class.
//    Can use either/or @EmbeddedId/@Id - mutually exclusive
    @EmbeddedId
    private NameId nameId;

    private String country;

    public AuthorEmbedded() {
    }

    public AuthorEmbedded(NameId nameId) {
        this.nameId = nameId;
    }

    public NameId getNameId() {
        return nameId;
    }

    public void setNameId(NameId nameId) {
        this.nameId = nameId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorEmbedded that = (AuthorEmbedded) o;

        return nameId.equals(that.nameId);
    }

    @Override
    public int hashCode() {
        return nameId.hashCode();
    }
}
