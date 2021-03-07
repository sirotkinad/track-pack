package com.trackpack.app.model.joining;

import com.trackpack.app.keys.UserParcelKey;
import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "UserParcel")
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

    //here will be additional fields

}
