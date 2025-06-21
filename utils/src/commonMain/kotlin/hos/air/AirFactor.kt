package hos.air

/**
 * <p>Title: AirFactor </p>
 * <p>Description: 定义空气质量因子的基本信息，如代码、显示符号、HTML 表示、单位等 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 14:02
 * @version : 1.0
 */
enum class AirFactor(
    val code: String,          // 因子代码，用于程序内部使用（如网络请求、数据库存储）
    val symbol: String,        // 显示用的标准化学符号
    val htmlSymbol: String,    // HTML 格式的化学符号
    val unit: String           // 单位
) {
    PM25("PM25", "PM₂.₅", "PM<sub><small><small>2.5</small></small></sub>", "μg/m³"),
    PM10("PM10", "PM₁₀", "PM<sub><small><small>10</small></small></sub>", "μg/m³"),
    PM1("PM1", "PM₁", "PM<sub><small><small>1</small></small></sub>", "μg/m³"),
    O3("O3", "O₃", "O<sub><small><small>3</small></small></sub>", "ppb"),
    NO2("NO2", "NO₂", "NO<sub><small><small>2</small></small></sub>", "ppb"),
    SO2("SO2", "SO₂", "SO<sub><small><small>2</small></small></sub>", "ppb"),
    NH3("NH3", "NH₃", "NH<sub><small><small>3</small></small></sub>", "ppb"),
    CO2("CO2", "CO₂", "CO<sub><small><small>2</small></small></sub>", "ppm"),
    VOC_S("VOCS", "VOCs", "VOC<sub><small><small>s</small></small></sub>", "μg/m³"),
    NOX("NOX", "NOx", "NO<sub><small><small>x</small></small></sub>", "ppb"),
    CO("CO", "CO", "CO", "ppm"),
    AQI("AQI", "AQI", "AQI", ""),
    O3NO2("O3NO2", "O₃NO₂", "O<sub><small><small>3</small></small></sub>NO<sub><small><small>2</small></small></sub>", "ppb");

    companion object {

        private val factorMap by lazy {
            mapOf(
                "PM25" to AirFactor.PM25,
                "PM2.5" to AirFactor.PM25,
                "PM₂.₅" to AirFactor.PM25,
                "pm25" to AirFactor.PM25,
                "pm2.5" to AirFactor.PM25,
                "pm₂.₅" to AirFactor.PM25,

                "PM10" to AirFactor.PM10,
                "PM₁₀" to AirFactor.PM10,

                "PM1" to AirFactor.PM1,
                "PM₁" to AirFactor.PM1,

                "O3" to AirFactor.O3,
                "o3" to AirFactor.O3,
                "O₃" to AirFactor.O3,

                "no2" to AirFactor.NO2,
                "no₂" to AirFactor.NO2,
                "NO2" to AirFactor.NO2,
                "NO₂" to AirFactor.NO2,

                "so2" to AirFactor.SO2,
                "so₂" to AirFactor.SO2,
                "SO2" to AirFactor.SO2,
                "SO₂" to AirFactor.SO2,

                "nh3" to AirFactor.NH3,
                "nh₃" to AirFactor.NH3,
                "NH3" to AirFactor.NH3,
                "NH₃" to AirFactor.NH3,

                "co2" to AirFactor.CO2,
                "co₂" to AirFactor.CO2,
                "CO2" to AirFactor.CO2,
                "CO₂" to AirFactor.CO2,

                "CO" to AirFactor.CO,
                "cO" to AirFactor.CO,
                "Co" to AirFactor.CO,
                "co" to AirFactor.CO,

                "AQI" to AirFactor.AQI,
                "aqi" to AirFactor.AQI,
                "Aqi" to AirFactor.AQI,

                "vocs" to AirFactor.VOC_S,
                "nox" to AirFactor.NOX,
                "o3no2" to AirFactor.O3NO2,
                "o₃no₂" to AirFactor.O3NO2,
                "VOCS" to AirFactor.VOC_S,
                "NOX" to AirFactor.NOX,
                "O3NO2" to AirFactor.O3NO2,
                "O₃NO₂" to AirFactor.O3NO2
            )
        }

        /**
         * 根据因子名称查找对应的 [AirFactor]
         *
         * @param factorName 因子名称（支持多种格式：纯文本、HTML、带单位等）
         * @return 匹配的 [AirFactor] 或 null
         */
        fun getAirFactor(factorName: String?): AirFactor? =
            if (factorName.isNullOrBlank()) null else factorMap[factorName]

        /**
         * 获取因子对应的单位
         *
         * @param factor 因子对象
         * @return 单位字符串
         */
        fun getUnit(factor: AirFactor): String = factor.unit

        /**
         * 根据因子名称获取其单位
         *
         * @param factorName 因子名称
         * @return 单位字符串
         */
        fun getUnitByFactorName(factorName: String?): String {
            return getAirFactor(factorName)?.unit ?: ""
        }
    }
}