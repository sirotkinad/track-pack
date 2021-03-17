package com.trackpack.app.model.joining;

import com.trackpack.app.keys.UserParcelKey;
import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@Table(name = "Userparcel")
@Data
@NoArgsConstructor
public class UserParcel {

    @EmbeddedId
    UserParcelKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @MapsId("parcelId")
    @JoinColumn(name = "parcelId")
    ShipmentTracking parcel;

    @NotNull
    private OffsetDateTime addDate;

    private boolean isFavourite = false;

}
