package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 17.03.2017 by K.N.K
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Friend {
    private int id;
    private int idFrom;
    private int idTo;
    private int status;
}
