package com.x.data.api

object Query {

    const val jobQuery: String = "_class," +
            "property[_class,parameterDefinitions[_class,name,description,type,defaultParameterValue[_class,name,value]]]," +
            "actions[_class,parameterDefinitions[_class,name,description,type,defaultParameterValue[_class,name,value]]]," +
            "jobs[_class,name,url,color]{0,500}," +
            "name," +
            "description," +
            "fullDisplayName," +
            "buildable," +
            "inQueue," +
            "keepDependencies," +
            "concurrentBuild," +
            "disabled," +
            "color," +
            "url," +
            "nextBuildNumber," +
            "firstBuild[_class,number,duration,timestamp,url]," +
            "lastBuild[_class,number,duration,timestamp,url]," +
            "lastCompletedBuild[_class,number,duration,timestamp,url]," +
            "lastFailedBuild[_class,number,duration,timestamp,url]," +
            "lastStableBuild[_class,number,duration,timestamp,url]," +
            "lastSuccessfulBuild[_class,number,duration,timestamp,url]," +
            "lastUnstableBuild[_class,number,duration,timestamp,url]," +
            "lastUnsuccessfulBuild[_class,number,duration,timestamp,url]," +
            "builds[_class,number,url,duration,timestamp,result]{0,500}," +
            "healthReport[iconClassName,description,iconUrl,score]"

    const val jobBuildQuery: String = "jobs[name,builds[number,duration,timestamp,result]]{0,100}"
}