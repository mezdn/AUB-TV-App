namespace Backend.Models.Interfaces
{
    interface IDisplayObject
    {
        public int Id { get; set; }
        public Type Type { get; set; }
        public string Category { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string YouTubeID { get; set; }
        public string ImageUrls { get; set; }
    }
    public enum Type { Video, SlideShow }
}
