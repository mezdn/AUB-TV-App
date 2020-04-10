namespace Backend.Models.Interfaces
{
    interface IEvent
    {
        public int Id { get; set; }
        public int Year { get; set; }
        public int Month { get; set; }
        public int Day { get; set; }
        public string Details { get; set; }
    }
}
