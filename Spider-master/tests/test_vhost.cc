#include <cassert>
#include <criterion/criterion.h>
#include <sstream>

#include "config/config.hh"
#include "vhost/vhost-factory.hh"
#include "vhost/vhost-static-file.hh"

/*
 *g++ -Wall -Wextra -lcriterion -std=c++17 -I ../src -I ../lib test_vhost.cc
 *../src/config/config.cc ../src/vhost/vhost-factory.cc
 *../src/vhost/vhost-static-file.cc ../src/vhost/dispatcher.cc
 *../src/request/response.cc ../src/request/header.cc -o test_vhost
 *
 * */

namespace http
{
    Test(VHOST, create_VHOST)
    {
        ServerConfig servconf = parse_configuration("config_file.json");
        auto my_host = VHostFactory::Create(servconf.vhconfs[0]);
        cr_expect(my_host->conf_get().vhost_ip == "127.0.0.1");
        cr_expect(my_host->conf_get().vhost_port == 8000);
        cr_expect(my_host->conf_get().vhost_server_name == "localhost");
        cr_expect(my_host->conf_get().vhost_root == ".");
        cr_expect(my_host->conf_get().vhost_def_file == "index.html");
    }

    /* Test(VHOST, test_response_true, .init = setup)
    {
        http::ServerConfig servconf =
    http::parse_configuration("config_file.json"); auto my_host =
    VHostFactory::Create(servconf.vhconfs[0]);

        Request req;
        req.method = "GET";
        req.uri = "/index.html";
        req.protocol_vers = "HTTP/1.1";
        req.message = "We don't care but at least it's here!";
        auto connection = std::make_shared<Connection>();
        my_host->respond(req,connection);
        Response expectedRes = Response(OK);
        std::cout << connection->resp.status.second << "\n";
        std::cout << expectedRes.status.second << "\n";
        cr_expect(connection->resp.status.second == expectedRes.status.second);
        //cr_expect(connection->resp.message == "<html>\n<h1>Hello from
    VHOST</h1>\n</html>");
    } */

} // namespace http
