@Test @SuppressWarnings("unchecked") public void testClient() throws Throwable {
  if (PerformanceUtils.getProperty("server",null) == null) {
    logger.warn("Please set -Dserver=127.0.0.1:9911");
    return;
  }
  final String server=System.getProperty("server","127.0.0.1:9911");
  final String transporter=PerformanceUtils.getProperty(Constants.TRANSPORTER_KEY,Constants.DEFAULT_TRANSPORTER);
  final String serialization=PerformanceUtils.getProperty(Constants.SERIALIZATION_KEY,Constants.DEFAULT_REMOTING_SERIALIZATION);
  final int timeout=PerformanceUtils.getIntProperty(Constants.TIMEOUT_KEY,Constants.DEFAULT_TIMEOUT);
  final int length=PerformanceUtils.getIntProperty("length",1024);
  final int connections=PerformanceUtils.getIntProperty(Constants.CONNECTIONS_KEY,1);
  final int concurrent=PerformanceUtils.getIntProperty("concurrent",100);
  int r=PerformanceUtils.getIntProperty("runs",10000);
  final int runs=r > 0 ? r : Integer.MAX_VALUE;
  final String onerror=PerformanceUtils.getProperty("onerror","continue");
  final String url="exchange://" + server + "?transporter="+ transporter+ "&serialization="+ serialization+ "&timeout="+ timeout;
  final ExchangeClient[] exchangeClients=new ExchangeClient[connections];
  for (int i=0; i < connections; i++) {
    exchangeClients[i]=Exchangers.connect(url);
  }
  List<String> serverEnvironment=(List<String>)exchangeClients[0].request("environment").get();
  List<String> serverScene=(List<String>)exchangeClients[0].request("scene").get();
  StringBuilder buf=new StringBuilder(length);
  for (int i=0; i < length; i++) {
    buf.append("A");
  }
  final String data=buf.toString();
  final AtomicLong count=new AtomicLong();
  final AtomicLong error=new AtomicLong();
  final AtomicLong time=new AtomicLong();
  final AtomicLong all=new AtomicLong();
  final CountDownLatch latch=new CountDownLatch(concurrent);
  for (int i=0; i < concurrent; i++) {
    new Thread(new Runnable(){
      public void run(){
        try {
          AtomicInteger index=new AtomicInteger();
          long init=System.currentTimeMillis();
          for (int i=0; i < runs; i++) {
            try {
              count.incrementAndGet();
              ExchangeClient client=exchangeClients[index.getAndIncrement() % connections];
              long start=System.currentTimeMillis();
              String result=(String)client.request(data).get();
              long end=System.currentTimeMillis();
              if (!data.equals(result)) {
                throw new IllegalStateException("Invalid result " + result);
              }
              time.addAndGet(end - start);
            }
 catch (            Exception e) {
              error.incrementAndGet();
              e.printStackTrace();
              if ("exit".equals(onerror)) {
                System.exit(-1);
              }
 else               if ("break".equals(onerror)) {
                break;
              }
 else               if ("sleep".equals(onerror)) {
                try {
                  Thread.sleep(30000);
                }
 catch (                InterruptedException e1) {
                }
              }
            }
          }
          all.addAndGet(System.currentTimeMillis() - init);
        }
  finally {
          latch.countDown();
        }
      }
    }
).start();
  }
  new Thread(new Runnable(){
    public void run(){
      try {
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        long lastCount=count.get();
        long sleepTime=2000;
        long elapsd=sleepTime / 1000;
        boolean bfirst=true;
        while (latch.getCount() > 0) {
          long c=count.get() - lastCount;
          if (!bfirst)           System.out.println("[" + dateFormat.format(new Date()) + "] count: "+ count.get()+ ", error: "+ error.get()+ ",tps:"+ (c / elapsd));
          bfirst=false;
          lastCount=count.get();
          Thread.sleep(sleepTime);
        }
      }
 catch (      Exception e) {
        e.printStackTrace();
      }
    }
  }
).start();
  latch.await();
  for (  ExchangeClient client : exchangeClients) {
    if (client.isConnected()) {
      client.close();
    }
  }
  long total=count.get();
  long failed=error.get();
  long succeeded=total - failed;
  long elapsed=time.get();
  long allElapsed=all.get();
  long clientElapsed=allElapsed - elapsed;
  long art=0;
  long qps=0;
  long throughput=0;
  if (elapsed > 0) {
    art=elapsed / succeeded;
    qps=concurrent * succeeded * 1000 / elapsed;
    throughput=concurrent * succeeded * length* 2* 1000 / elapsed;
  }
  PerformanceUtils.printBorder();
  PerformanceUtils.printHeader("Dubbo Remoting Performance Test Report");
  PerformanceUtils.printBorder();
  PerformanceUtils.printHeader("Test Environment");
  PerformanceUtils.printSeparator();
  for (  String item : serverEnvironment) {
    PerformanceUtils.printBody("Server " + item);
  }
  PerformanceUtils.printSeparator();
  List<String> clientEnvironment=PerformanceUtils.getEnvironment();
  for (  String item : clientEnvironment) {
    PerformanceUtils.printBody("Client " + item);
  }
  PerformanceUtils.printSeparator();
  PerformanceUtils.printHeader("Test Scene");
  PerformanceUtils.printSeparator();
  for (  String item : serverScene) {
    PerformanceUtils.printBody("Server " + item);
  }
  PerformanceUtils.printBody("Client Transporter: " + transporter);
  PerformanceUtils.printBody("Serialization: " + serialization);
  PerformanceUtils.printBody("Response Timeout: " + timeout + " ms");
  PerformanceUtils.printBody("Data Length: " + length + " bytes");
  PerformanceUtils.printBody("Client Shared Connections: " + connections);
  PerformanceUtils.printBody("Client Concurrent Threads: " + concurrent);
  PerformanceUtils.printBody("Run Times Per Thread: " + runs);
  PerformanceUtils.printSeparator();
  PerformanceUtils.printHeader("Test Result");
  PerformanceUtils.printSeparator();
  PerformanceUtils.printBody("Succeeded Requests: " + DecimalFormat.getIntegerInstance().format(succeeded));
  PerformanceUtils.printBody("Failed Requests: " + failed);
  PerformanceUtils.printBody("Client Elapsed Time: " + clientElapsed + " ms");
  PerformanceUtils.printBody("Average Response Time: " + art + " ms");
  PerformanceUtils.printBody("Requests Per Second: " + qps + "/s");
  PerformanceUtils.printBody("Throughput Per Second: " + DecimalFormat.getIntegerInstance().format(throughput) + " bytes/s");
  PerformanceUtils.printBorder();
}
