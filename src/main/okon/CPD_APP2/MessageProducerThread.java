package okon.CPD_APP2;

import static okon.CPD_APP2.CpdApp2App.*;

public class MessageProducerThread extends Thread {

    @Override
    public void run() {
        while (!webserviceQueue.isEmpty()) {
            HttpDetailsJob job = null;

            synchronized (webserviceQueue) {
                if (!webserviceQueue.isEmpty()) {
                    job = webserviceQueue.poll();
                }
            }

            if (job != null) {
                Message message = checkService(job, checkingSumForWebservice);

                synchronized (messageList) {
                    messageList.add(message);
                }
            }
        }
    }

    private Message checkService(HttpDetailsJob job, int checkingSum) {
        int correctChecksCounter = 0;
        ConnectionFactory connectionFactory = chooseFactory(job);

        for (int i = 0; i < checkingSum; i++) {
            if (isCorrectService(job, connectionFactory)) {
                correctChecksCounter++;
            }
        }

        return new Message(job.getUrl(), job.getDescription(), correctChecksCounter, checkingSum);
    }

    private ConnectionFactory chooseFactory(HttpDetailsJob job) {
        if (job.getUrl().contains("http://") && job.getLogin() == null) {
            return httpConnectionFactory;
        } else if (job.getUrl().contains("http://") && job.getLogin() != null) {
            return domainHttpConnectionFactory;
        } else if (job.getUrl().contains("https://") && job.getLogin() == null) {
            return httpsConnectionFactory;
        } else {
            return domainHttpsConnectionFactory;
        }
    }

    private boolean isCorrectService(HttpDetailsJob job, ConnectionFactory connectionFactory) {
        try (Connection connection = connectionFactory.build(job)) {
            try {
                String response = connection.response();
                if (response != null) {
                    return true;
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}