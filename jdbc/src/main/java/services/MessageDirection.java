package services;

import lombok.Getter;
import lombok.ToString;

/**
 * Внутренний класс представляющий собой простую карту,
 * где count - кол-во новых сообщений, fromId - id user-a который не прочитал сообщения
 * <p>
 * 19.03.2017 by K.N.K
 */
@ToString
@Getter
public class MessageDirection {
    private int fromId;
    private int toId;
    private int count;

    public MessageDirection(int fromId, int toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MessageDirection)) return false;
        final MessageDirection other = (MessageDirection) o;
        if ((this.fromId == other.fromId && this.toId == other.toId)) {
            return true;
        } else //noinspection RedundantIfStatement
            if (this.fromId == other.toId && this.toId == other.fromId) {
                return true;
            } else return false;
    }

    public MessageDirection setCount(int count) {
        this.count = count;
        return this;
    }

}
