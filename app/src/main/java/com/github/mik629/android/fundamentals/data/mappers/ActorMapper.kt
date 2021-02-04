package com.github.mik629.android.fundamentals.data.mappers

import com.github.mik629.android.fundamentals.data.network.model.ActorDTO
import com.github.mik629.android.fundamentals.domain.model.ActorItem

class ActorMapper : Mapper<ActorDTO, ActorItem> {
    override fun map(obj: ActorDTO) = ActorItem(
        obj.id, obj.name, obj.avaPath
    )

    override fun reverseMap(obj: ActorItem): ActorDTO {
        throw UnsupportedOperationException("Not yet implemented")
    }
}