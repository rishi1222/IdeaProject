package com.thomsonreuters

import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

/**
 * Created by rishikapoor on 28/07/2016.
 */
class Compare  {


  def compareRDD [T: ClassTag](expected: RDD[T], actual: RDD[T]): RDD[(T, (Int, Int))]  = {
    var result:RDD[(T, (Int, Int))] = null
    try {

      // Key the values and count the number of each unique element

        val expectedKeyed = expected.map(x => (x, 1)).reduceByKey(_ + _)
        val resultKeyed = actual.map(x => (x, 1)).reduceByKey(_ + _)

      // Group them together and filter for difference
      //Filter would return an RDD where the values are empty or do not match each other


        val result =  expectedKeyed.cogroup(resultKeyed).filter{ case (_, (i1, i2)) =>
                            i1.isEmpty || i2.isEmpty || i1.head != i2.head
        }
        //.take(1).headOption
        .map { case (v, (i1, i2)) => (v, (i1.headOption.getOrElse(0), i2.headOption.getOrElse(0))) }


            return result

    }catch{
            case ex:Exception=>{println("The Exception Occured in CompareRdd" , ex)
            throw new Exception}

          }

  }

}
