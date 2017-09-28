package com.thomsonreuters

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by rishikapoor on 26/07/2016.
 */
class CompareRdf() extends AbstractComparator() {



  def mainCall(args :Array[String]): Unit ={
    val config = new SparkConf().setAppName(myConfig.envOrElseConfig("my.secret.spark.app.name")).setMaster(myConfig.envOrElseConfig("my.secret.spark.master"))
    config.registerKryoClasses(Array(classOf[org.apache.hadoop.io.LongWritable], classOf[org.apache.hadoop.io.Text]))
    val sc = new SparkContext(config)

    try {
          new DeleteOutputFiles(conf).deleteFile
          val expected = new ReturnRdfRdd(conf).getRdfRdd(args{0}, sc)
          val actual = new ReturnRdfRdd(conf).getRdfRdd(args{1}, sc)
          val pairsC = expected.map(x => (x.toString().split(","))).map(y => (y(1)))
          val pairsC1 = actual.map(x => (x.toString().split(","))).map(x => (x(1)))

          val difference = new Compare().compareRDD(pairsC, pairsC1)

          new ReportDiff().reportRdfDiff(difference,args{2},args{3},args{4},sc)

          //difference.foreach(println)


      } catch {
                case ex: Exception=>{
                println("Error in main", ex)
                throw new RuntimeException
                }
              }

  }


}
