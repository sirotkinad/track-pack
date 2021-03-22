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
@Table(name = "user_parcel")
@Data
@NoArgsConstructor
public class UserParcel {

    @EmbeddedId
    UserParcelKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("parcelId")
    @JoinColumn(name = "parcel_id")
    ShipmentTracking parcel;

    @NotNull
    private OffsetDateTime addDate;

    private boolean isFavourite = false;

}
