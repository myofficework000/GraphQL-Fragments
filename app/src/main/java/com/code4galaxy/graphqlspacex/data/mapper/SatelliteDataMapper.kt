package com.code4galaxy.graphqlspacex.data.mapper

import com.code4galaxy.graphqlspacex.data.dto.SatelliteData
import com.codegalaxy.graphqldemo.model.FetchSpaceXListQuery

fun mapToSatelliteData(satellite: FetchSpaceXListQuery.LaunchesPast?): SatelliteData? {
    return if (satellite != null) {
        SatelliteData(
            missionName = satellite.launchDetails.mission_name.orEmpty(),
            launchDate = satellite.launchDetails.launch_date_local.toString(),
            launchSite = satellite.launchDetails.launch_site?.site_name_long.orEmpty(),
            articleLink = satellite.launchDetails.links?.article_link,
            videoLink = satellite.launchDetails.links?.video_link,
            rocketName = satellite.launchDetails.rocket?.rocket_name.orEmpty()
        )
    } else{
        null
    }
}