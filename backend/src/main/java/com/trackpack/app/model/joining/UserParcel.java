package com.trackpack.app.model.joining;

import com.trackpack.app.keys.UserParcelKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_parcel", schema = "\"track-pack-db\"")
@Data
@NoArgsConstructor
public class UserParcel {

    @EmbeddedId
    UserParcelKey id;

    private String parcelName;

    private OffsetDateTime addDate;

    private boolean isFavourite = false;

    public UserParcel(UserParcelKey id) {
        this.id = id;
    }

}
