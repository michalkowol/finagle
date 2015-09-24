package pl.michalkowol

import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import thrift.{Calculator, PingPong}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

object FinagleServer {
  def main(args: Array[String]): Unit = {
    val finagle = new FinagleServer()
    Await.all(finagle.calculatorServer, finagle.pingPongServer)
  }
}

class FinagleServer(implicit ec: ExecutionContext) {
  private[this] val calculator = new Calculator.FutureIface {
    override def multiply(num1: Int, num2: Int): Future[Int] = Future { num1 * num2 }
    override def add(num1: Int, num2: Int): Future[Int] = Future { num1 + num2 }
  }
  private[this] val pingPong = new PingPong.FutureIface {
    override def ping(): Future[String] = Future {
      println("ping")
      "pong"
    }
    override def pong(): Future[String] = Future {
      println("pong")
      "ping"
    }
  }
  val calculatorServer = Thrift.serveIface(":8080", calculator)
  val pingPongServer = Thrift.serveIface(":8082", pingPong)
}

object FinagleClient {
  def main(args: Array[String]): Unit = {
    val calculator = Thrift.newIface[Calculator.FutureIface]("192.168.0.15:8080")
    val pingPong = Thrift.newIface[PingPong.FutureIface]("192.168.0.15:8082")
    val ping = pingPong.ping()
    val pong = pingPong.pong()
    val result = calculator.add(1, 2)
    println(Await.result(Future.join(ping, pong, result)))
  }
}
