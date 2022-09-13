#include <cassert>
#include <criterion/criterion.h>
#include <iostream>
#include <sstream>

#include "config/config.hh"

/*
 *g++ -Wall -Wextra -Werror -pedantic -lcriterion -std=c++17 -I ../src -I ../lib
 *test_config.cc ../src/config/config.cc ../src/vhost/dispatcher.cc
 *../src/vhost/vhost-factory.cc ../src/vhost/vhost-static-file.cc
 *../src/request/header.cc ../src/request/response.cc -o test_config
 *
 * */

namespace http
{
    Test(vhost, constructor)
    {
        int argc = 2;
        char **argv = ["./spider", "./tests/config_file.json"];
        http::ServerConfig servconf = http::parse_configuration(argc, argv);

        cr_expect(servconf.vhconfs[0].vhost_ip == "127.0.0.1");
        cr_expect(servconf.vhconfs[0].vhost_port == 8000);
        cr_expect(servconf.vhconfs[0].vhost_server_name == "localhost");
        cr_expect(servconf.vhconfs[0].vhost_root == ".");
        cr_expect(servconf.vhconfs[0].vhost_def_file == "index.html");
        cr_expect(servconf.vhconfs[0].vhost_ssl_cert == "");
        cr_expect(servconf.vhconfs[0].vhost_ssl_key == "");
        cr_expect(servconf.vhconfs[0].vhost_auth_basic == "");
        cr_expect(servconf.vhconfs[0].vhost_auth_basic_users.size() == 0);
        cr_expect(servconf.vhconfs[0].vhost_def_vh == false);

        cr_expect(servconf.vhconfs[1].vhost_ip == "127.0.0.2");
        cr_expect(servconf.vhconfs[1].vhost_port == 8005);
        cr_expect(servconf.vhconfs[1].vhost_server_name == "localhostserver");
        cr_expect(servconf.vhconfs[1].vhost_root == "rootserver");
        cr_expect(servconf.vhconfs[1].vhost_def_file == "myfile.html");
        cr_expect(servconf.vhconfs[1].vhost_ssl_cert == "certs/localhost.pem");
        cr_expect(servconf.vhconfs[1].vhost_ssl_key
                  == "certs/localhost-key.pem");
        cr_expect(servconf.vhconfs[1].vhost_auth_basic == "");
        cr_expect(servconf.vhconfs[1].vhost_auth_basic_users.size() == 0);
        cr_expect(servconf.vhconfs[1].vhost_def_vh == false);

        cr_expect(servconf.vhconfs[2].vhost_ip == "2001:db8:8:4::1");
        cr_expect(servconf.vhconfs[2].vhost_port == 8008);
        cr_expect(servconf.vhconfs[2].vhost_server_name == "defaultserver");
        cr_expect(servconf.vhconfs[2].vhost_root == "defaultroot");
        cr_expect(servconf.vhconfs[2].vhost_def_file == "index.html");
        cr_expect(servconf.vhconfs[2].vhost_ssl_cert == "");
        cr_expect(servconf.vhconfs[2].vhost_ssl_key == "");
        cr_expect(servconf.vhconfs[2].vhost_auth_basic
                  == "Please log in to default_server");
        cr_expect(servconf.vhconfs[2].vhost_auth_basic_users.size() == 1);
        cr_expect(servconf.vhconfs[2].vhost_auth_basic_users[0]
                  == "admin-spider");
        cr_expect(servconf.vhconfs[2].vhost_def_vh == true);
    }
} // namespace http
