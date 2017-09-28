package com.thomsonreuters

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * Created by rishikapoor on 29/07/2016.
 */
class ReportDiff {

  def reportRdfDiff(expected:RDD[(String,(Int,Int))],missingActual:String,missingExpected:String,duplicates:String,sc:SparkContext) = {
    try {

        expected.filter{ case(_,(l2,l3))=> l2==0 && l3<2}.saveAsTextFile(missingActual)
        expected.filter{ case(_,(l2,l3))=> l3==0 && l2<2}.saveAsTextFile(missingExpected)
        expected.filter{ case(_,(l2,l3))=> l2>=2 || l3>=2}.saveAsTextFile(duplicates)
//      expected.foreach(x => x match {
//        case (l1, (1, 0)) => sc.parallelize(x).saveAsTextFile(missingActual)
//        case (l1, (0, 1)) => sc.parallelize(x._1).saveAsTextFile(missingExpected)
//        case _ =>sc.parallelize(x._1).saveAsTextFile(duplicates)
//      })
    }catch {
      case ex:Exception=>{println("Exception Occured in ReportDiff",ex)
      throw new Exception}
    }
  }

}
