package org.alexdev.havana.messages.outgoing.effects;

import org.alexdev.havana.game.effects.Effect;
import org.alexdev.havana.messages.types.PlayerMessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class AVATAR_EFFECTS extends PlayerMessageComposer {
    private final List<Effect> effects;

    public AVATAR_EFFECTS(List<Effect> effects) {
        this.effects = effects;
    }

    /*

        public function _SafeStr_2762(_arg_1:_SafeStr_610):Boolean
        {
            var _local_4:_SafeStr_85;
            _SafeStr_6493 = new Array();
            var _local_2:int = _arg_1.ReadInteger();
            var _local_3:int;
            while (_local_3 < _local_2) {
                _local_4 = new _SafeStr_85();
                _local_4.type = _arg_1.ReadInteger();
                _local_4.duration = _arg_1.ReadInteger();
                _local_4._SafeStr_3430 = _arg_1.ReadInteger();
                _local_4._SafeStr_3431 = _arg_1.ReadInteger();
                _SafeStr_6493.push(_local_4);
                _local_3++;
            };
            return (true);
        }

     */

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.effects.size());

        for (Effect effect : this.effects) {
            response.writeInt(effect.getEffectId());
            response.writeInt(effect.getTimeDuration());
            response.writeInt((int) this.effects.stream().filter(e -> e.getEffectId() == effect.getEffectId()).count());
            response.writeInt(effect.isActivated() ? effect.getTimeLeft() : -1);
        }
    }

    @Override
    public short getHeader() {
        return 460; // "GL"
    }
}
