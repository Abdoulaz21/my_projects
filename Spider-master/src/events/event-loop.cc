#include "events/event-loop.hh"

#include "events/events.hh"

namespace http
{
    EventLoop::EventLoop()
    {
        loop = ev_default_loop(EVFLAG_AUTO);
    }

    EventLoop::EventLoop(struct ev_loop *evloop)
    {
        loop = evloop;
    }

    EventLoop::~EventLoop()
    {
        ev_loop_destroy(loop);
    }

    void EventLoop::register_watcher(EventWatcher *ewatch)
    {
        ev_io_start(loop, &ewatch->watcher_get());
    }

    void EventLoop::unregister_watcher(EventWatcher *ewatch)
    {
        ev_io_stop(loop, &ewatch->watcher_get());
    }

    static void sigint_cb(struct ev_loop *loop, ev_signal *, int)
    {
        ev_break(loop, EVBREAK_ALL);
    }

    void EventLoop::register_sigint_watcher(ev_signal *esignal) const
    {
        ev_signal_init(esignal, sigint_cb, SIGINT);
        ev_signal_start(loop, esignal);
    }

    void EventLoop::operator()() const
    {
        ev_run(loop, 0);
    }
} // namespace http
