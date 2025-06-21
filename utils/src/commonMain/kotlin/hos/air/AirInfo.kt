package hos.air

/**
 * <p>Title: AirInfo </p>
 * <p>Description: 空气质量信息数据类，包含因子与等级 </p>
 * <p>Company: www.hos.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2025-06-12 14:01
 * @version : 1.0
 */

data class AirInfo(val factor: AirFactor, val level: AirLevel?) {

    /**
     * 获取带单位的值字符串
     *
     * @param value 数值
     * @return 形如 "50μg/m³"
     */
    fun getValueWithUnit(value: Double): String = "$value${factor.unit}"

    companion object {

        /**
         * 获取 24 小时平均值对应的空气质量信息
         *
         * @param factor 因子名称
         * @param value 数值
         * @return [AirInfo] 实例或 null
         */
        fun getAirInfo24(factor: String?, value: Double?): AirInfo? {
            return getAirInfo(factor, value) { f ->
                when (f) {
                    AirFactor.AQI -> AirLevelThreshold.AQI.getLevel(value)
                    AirFactor.PM25 -> AirLevelThreshold.PM25.getLevel(value)
                    AirFactor.PM10 -> AirLevelThreshold.PM10.getLevel(value)
                    AirFactor.SO2 -> AirLevelThreshold.SO2_24.getLevel(value)
                    AirFactor.NO2 -> AirLevelThreshold.NO2_24.getLevel(value)
                    AirFactor.O3 -> AirLevelThreshold.O3_8.getLevel(value)
                    AirFactor.CO -> AirLevelThreshold.CO_24.getLevel(value)
                    else -> null
                }
            }
        }

        /**
         * 获取 1 小时平均值对应的空气质量信息
         *
         * @param factor 因子名称
         * @param value 数值
         * @return [AirInfo] 实例或 null
         */
        fun getAirInfo1(factor: String?, value: Double?): AirInfo? {
            return getAirInfo(factor, value) { f ->
                when (f) {
                    AirFactor.AQI -> AirLevelThreshold.AQI.getLevel(value)
                    AirFactor.PM25 -> AirLevelThreshold.PM25.getLevel(value)
                    AirFactor.PM10 -> AirLevelThreshold.PM10.getLevel(value)
                    AirFactor.SO2 -> AirLevelThreshold.SO2_1.getLevel(value)
                    AirFactor.NO2 -> AirLevelThreshold.NO2_1.getLevel(value)
                    AirFactor.O3 -> AirLevelThreshold.O3_1.getLevel(value)
                    AirFactor.CO -> AirLevelThreshold.CO_1.getLevel(value)
                    else -> null
                }
            }
        }

        /**
         * 提取公共逻辑，获取空气质量信息
         *
         * @param factor 因子名称
         * @param value 数值
         * @param levelSelector 等级选择函数
         * @return [AirInfo] 实例或 null
         */
        private fun getAirInfo(
            factor: String?,
            value: Double?,
            levelSelector: (AirFactor) -> AirLevel?
        ): AirInfo? {
            val airFactor = AirFactor.getAirFactor(factor) ?: return null
            val level = levelSelector(airFactor)
            return AirInfo(airFactor, level)
        }
    }
}
