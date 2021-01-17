package com.github.mik629.android.fundamentals.data.mappers

import com.github.mik629.android.fundamentals.data.network.model.Actor
import com.github.mik629.android.fundamentals.domain.model.ActorItem

class ActorMapper : Mapper<Actor, ActorItem> {
    override fun map(obj: Actor) = ActorItem(
        obj.id, obj.name, obj.avaPath
    )

    override fun reverseMap(obj: ActorItem): Actor {
        throw UnsupportedOperationException("Not yet implemented")
    }
}