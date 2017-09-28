package com.thomsonreuters

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

/**
 * Created by rishikapoor on 29/07/2016.
 */
class DeleteOutputFiles(conf:Configuration){

  def deleteFile ={
  val fs: FileSystem = FileSystem.get(conf)
  if (fs.exists(new Path("hdfs://Venus/project/ecpdemolinkeddata/SupportUtility/missingActual"))) {
    fs.delete(new Path("hdfs://Venus/project/ecpdemolinkeddata/SupportUtility/missingActual"), true)
    fs.delete(new Path("hdfs://Venus/project/ecpdemolinkeddata/SupportUtility/mssingExpected"), true)
    fs.delete(new Path("hdfs://Venus/project/ecpdemolinkeddata/SupportUtility/duplicates"), true)
    }
  }


}
