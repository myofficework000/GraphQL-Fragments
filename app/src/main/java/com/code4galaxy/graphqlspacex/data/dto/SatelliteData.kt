package com.code4galaxy.graphqlspacex.data.dto

data class SatelliteData(
    val missionName: String,
    val launchDate: String,
    val launchSite: String,
    val articleLink: String?,
    val videoLink: String?,
    val rocketName: String
)