#include "events/register.hh"

#include <unordered_map>

#include "events/event-loop.hh"

namespace http
{
    EventWatcherRegistry event_register;

    bool EventWatcherRegistry::unregister_ew(EventWatcher *evw)
    {
        ssize_t count = events_.erase(evw);
        if (count == 1)
            loop_.unregister_watcher(evw);
        return count == 1;
    }

    std::optional<std::shared_ptr<EventWatcher>>
    EventWatcherRegistry::at(EventWatcher *evw)
    {
        auto search = events_.find(evw);
        if (search != events_.end())
            return search->second;
        return std::nullopt;
    }

    EventLoop &EventWatcherRegistry::loop_get()
    {
        return loop_;
    }
} // namespace http
