package com.thomsonreuters

/**
 * Created by rishikapoor on 29/07/2016.
 */

import java.io.IOException

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.SecurityUtil

abstract class AbstractComparator(){

  val myConfig = new MyConfig(Some("kerberos"))

    println("Enetring Abstract")
   var conf:Configuration = new Configuration()
    if("local".equals(myConfig.envOrElseConfig("my.secret.spark.master"))) {
      hadoopLogin
    }


  def hadoopLogin() {

    println("calling kerberos")

    val USER_KEY: String = "appuser.principal"
    val KEYTAB_KEY: String = "appuser.keytab.filename"

    // login with keytab
    val principal: String = "bigdata-app-ecpdemolinkeddata-srvc@INTPROD.THOMSONREUTERS.COM"
    val keytab: String = System.getProperty("user.dir") + "/src/main/resources/bigdata-app-ecpdemolinkeddata-srvc.keytab"

    conf.set(USER_KEY, principal)
    conf.set(KEYTAB_KEY, keytab)
    try {
      SecurityUtil.login(conf,KEYTAB_KEY,USER_KEY)
      println(" Trying to connect ..........")
    } catch {
      case ex: IOException => ex.printStackTrace()
    }



  }

}
