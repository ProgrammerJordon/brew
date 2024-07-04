package brew.cmm.service.rch;

import brew.cmm.service.log.BrewBasicLogger;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class BrewResourceCloseHelper {

    /**
     * Resource close 처리.
     * @param resources
     */
    public static void close(Closeable... resources) {
        for (Closeable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException ignore) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
                    BrewBasicLogger.ignore("Occurred IOException to close resource is ingored!!");
                }
            }
        }
    }

    /**
     * JDBC 관련 resource 객체 close 처리
     * @param objects
     */
    public static void closeDBObjects(Wrapper... objects) {
        for (Object object : objects) {
            if (object != null) {
                if (object instanceof ResultSet) {
                    try {
                        ((ResultSet)object).close();
                    } catch (SQLException ignore) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
                        BrewBasicLogger.ignore("Occurred SQLException to close resource is ingored!!");
                    }
                } else if (object instanceof Statement) {
                    try {
                        ((Statement)object).close();
                    } catch (SQLException ignore) {//KISA 보안약점 조치 (2018-10-29, 윤창원)
                        BrewBasicLogger.ignore("Occurred SQLException to close resource is ingored!!");
                    }
                } else if (object instanceof Connection) {
                    try {
                        ((Connection)object).close();
                    } catch (SQLException ignore) {
                        BrewBasicLogger.ignore("Occurred SQLException to close resource is ingored!!");
                    }
                } else {
                    throw new IllegalArgumentException("Wrapper type is not found : " + object.toString());
                }
            }
        }
    }


    public static void closeSocketObjects(Socket socket, ServerSocket server) {
        if (socket != null) {
            try {
                socket.shutdownOutput();
            } catch (IOException ignore) {
                BrewBasicLogger.ignore("Occurred IOException to close resource is ingored!!");
            }

            try {
                socket.close();
            } catch (IOException ignore) {
                BrewBasicLogger.ignore("Occurred IOException to close resource is ingored!!");
            }
        }

        if (server != null) {
            try {
                server.close();
            } catch (IOException ignore) {
                BrewBasicLogger.ignore("Occurred IOException to close resource is ingored!!");
            }
        }
    }

    /**
     *  Socket 관련 resource 객체 close 처리
     *
     * @param sockets
     */
    public static void closeSockets(Socket ... sockets) {
        for (Socket socket : sockets) {
            if (socket != null) {
                try {
                    socket.shutdownOutput();
                } catch (IOException ignore) {
                    BrewBasicLogger.ignore("Occurred IOException to close resource is ingored!!");
                }

                try {
                    socket.close();
                } catch (IOException ignore) {
                    BrewBasicLogger.ignore("Occurred IOException to close resource is ingored!!");
                }
            }
        }
    }
}
