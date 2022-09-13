#include "config/config.hh"
#include "error/not-implemented.hh"
#include "events/events.hh"
#include "events/listener.hh"
#include "events/register.hh"
#include "init/init.hh"
#include "misc/addrinfo/addrinfo.hh"
#include "misc/readiness/readiness.hh"
#include "misc/sys-wrapper.hh"
#include "socket/default-socket.hh"
#include "socket/socket.hh"
#include "vhost/dispatcher.hh"
#include "vhost/vhost-factory.hh"

using namespace http;
int main(int argc, char **argv)
{
    struct ServerConfig servconfig = parse_configuration(argc, argv);

    const auto &eloopRegister = event_register.loop_get();

    ev_signal exitsig;
    eloopRegister.register_sigint_watcher(&exitsig);

    misc::announce_spider_readiness(argv[0]);

    eloopRegister();

    return 0;
}
