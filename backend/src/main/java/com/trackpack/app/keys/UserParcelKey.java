package com.trackpack.app.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserParcelKey implements Serializable {

    UUID userId;
    UUID parcelId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserParcelKey that = (UserParcelKey) o;
        return userId.equals(that.userId) && parcelId.equals(that.parcelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, parcelId);
    }

}
