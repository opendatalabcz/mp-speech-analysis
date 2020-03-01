package poslanciDB.entity;

import java.io.Serializable;

public interface HasID extends Serializable {
    public Integer takeID();
    public void pushID(Integer id);
}
