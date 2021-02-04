package com.github.mik629.android.fundamentals.data.mappers

import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.domain.model.Actor

class ActorMapper : Mapper<ActorDTO, Actor> {
    override fun map(obj: ActorDTO) = Actor(
        obj.id, obj.name, obj.avaPath
    )

    override fun reverseMap(obj: Actor): ActorDTO {
        throw UnsupportedOperationException("Not yet implemented")
    }
}