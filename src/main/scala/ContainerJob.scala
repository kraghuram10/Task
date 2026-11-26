import java.time.LocalDateTime

case class ContainerJob(jobId: String, containerId: String,
                        jobType: JobType, jobTime: LocalDateTime,
                        duration: Int, blockName: String)

