package com.github.eutkin.bot2gis

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.ResultSet

@RestController
@RequestMapping("/api/poi")
class EndpointPOI(
    private val jdbc: JdbcOperations,
    private val tg: TgAuth
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val rm = POIrm()
    }

    @GetMapping
    fun get(@RequestHeader("X-Telegram-Init-Data", required = false) tgInitData: String?): List<POI> {
        val user = tgInitData?.run(this.tg::extractTgUser) ?: return getAllPOI()
        return getAllPOI(user.id)
    }


    private fun getAllPOI(userId: Long): List<POI> =
        this.jdbc.query({ pss ->
            val ps = pss.prepareStatement(
                """
            select p.*, up.status 
            from POIs p 
                right join user_POIs up on p.id = up.location_id 
            where up.user_id = ?"""
            )
            ps.setLong(1, userId)
            ps
        }, rm)

    private fun getAllPOI(): List<POI> = this.jdbc.query("select *, 'locked' as status from POIs", rm)
}

internal class POIrm : RowMapper<POI> {
    override fun mapRow(rs: ResultSet, rowNum: Int): POI? {
        return POI(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getInt("points"),
            rs.getString("quote"),
            rs.getString("description"),
            rs.getString("imageUrl"),
            rs.getDouble("latitude"),
            rs.getDouble("longitude"),
            rs.getInt("radius_m"),
            rs.getString("status"),

            )
    }
}

data class POI(
    val id: Int,
    val title: String,
    val points: Int,
    val quote: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
    val status: String,
)