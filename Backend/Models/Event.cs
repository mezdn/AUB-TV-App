using Backend.Models.Interfaces;

namespace Backend.Models
{
    public class Event : IEvent
    {
        public int Id { get; set; }
        public int Year { get; set; }
        public int Month { get; set; }
        public int Day { get; set; }
        public string Details { get; set; }
    }
}
