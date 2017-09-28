package com.thomsonreuters

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.io.LongWritable
import org.apache.jena.hadoop.rdf.io.input.QuadsInputFormat
import org.apache.jena.hadoop.rdf.types.QuadWritable
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD



/**
 * Created by rishikapoor on 26/07/2016.
 */
class ReturnRdfRdd(conf:Configuration) {


  def getRdfRdd(filePath: String, sc: SparkContext): RDD[(LongWritable, QuadWritable)] = {
    println(filePath)
    //loadCoreSite(conf)

    val data = sc.newAPIHadoopFile(filePath,
      classOf[QuadsInputFormat],
      classOf[LongWritable], //position
      classOf[QuadWritable], //value
      conf)

    return data
  }


}
