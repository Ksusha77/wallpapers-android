package com.kseniya.wallpapers.data.mapper

import com.kseniya.wallpapers.data.datasource.local.entity.CollectionEntity
import com.kseniya.wallpapers.data.datasource.remote.dto.CollectionDto
import com.kseniya.wallpapers.domain.model.Collection

internal fun Collection(
    dto: CollectionDto
): Collection = with(dto) {
    Collection(
        id = id,
        title = title
    )
}

internal fun CollectionEntity(
    item: Collection
): CollectionEntity = with(item) {
    CollectionEntity(
        title = title
    )
}

internal fun Collection(
    entity: CollectionEntity
): Collection = with(entity) {
    Collection(
        id = id.toString(),
        title = title
    )
}