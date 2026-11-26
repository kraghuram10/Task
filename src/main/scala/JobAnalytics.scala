import java.time.{LocalDate, LocalDateTime}

object JobAnalytics {
  def dailyBreakdown(jobs: List[ContainerJob], date: LocalDate): Unit = {
    val res = jobs
      .filter(x => x.jobTime.toLocalDate == date)
      .groupBy(x => x.jobTime.getHour)
      .toSeq
      .sortBy(x => x._1)
    println(s"Date: $date")
    res.foreach { case (hour, hourlyJobs) =>
      println(s"\nHour: $hour")
      println("ContainerId\tJobType\tDurationMin\tBlockName")
      hourlyJobs.sortBy(_.jobTime).foreach { job =>
        println(s"${job.containerId}\t${job.jobType}\t${job.duration}\t${job.blockName}")
      }
    }
  }

  def hourlyBreakdown(jobs: List[ContainerJob], date: LocalDate, hour: Int): Unit = {
    val res = jobs.filter(x => x.jobTime.toLocalDate == date && x.jobTime.getHour == hour)

    println(s"Date: $date\nHour: $hour\nContainer list:\nContainerId\tJobType\tDurationMin\tBlockName")
    res.foreach { job =>
      println(
        s"${job.containerId}\t${job.jobType}\t${job.duration}\t${job.blockName}"
      )
    }
  }

  def countHourlyBreakdown(jobs: List[ContainerJob], date: LocalDate, hour: Int, jobType: JobType): Unit={
    val res = jobs.count(x=>x.jobTime.toLocalDate == date && x.jobTime.getHour==hour && x.jobType==jobType)
    println(res)
  }

  def totalJobCountByType(jobs: List[ContainerJob],startTime: LocalDateTime,endTime: LocalDateTime): Unit = {
    val res = jobs.filter(x => !x.jobTime.isBefore(startTime) && !x.jobTime.isAfter(endTime))
      .groupBy(_.jobType)
    res.foreach(x=>println(s"Type ${x._1} -> ${x._2.size}"))
  }

  def hourlyJobsByBlock(
                         jobs: List[ContainerJob],
                         date: LocalDate,
                         block: String
                       ): Map[Int, Map[JobType, Int]] = {

    jobs.filter(x => x.jobTime.toLocalDate == date && x.blockName == block)
      .groupBy(_.jobTime.getHour)
      .map { case (hour, hourlyJobs) =>
        hour -> hourlyJobs.groupBy(_.jobType).map {
          case (jobType, jobList) => jobType -> jobList.size
        }
      }
  }

  def blockData(jobs: List[ContainerJob]): Unit = {
    val res = jobs.groupBy(_.blockName)
      .map { case (blockName, jobsList) =>
        blockName -> jobsList.size
      }
    println(res.maxBy(_._2))
    println(res.minBy(_._2))
  }
}