using Backend.Models.Interfaces;
using Microsoft.AspNetCore.Http;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Backend.Models
{
    public class DisplayObject : IDisplayObject
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string Category { get; set; }
        public Type Type { get; set; }
        [Display(Name = "YouTube ID")]
        public string YouTubeID { get; set; }
        [Display(Name = "Image Url")]
        public string ImageUrls { get; set; } // Urls to be separated by ';'

        [NotMapped]
        public List<IFormFile> Pictures { get; set; }

        [NotMapped]
        public string shortHandUrls
        {
            get
            {
                if (this.ImageUrls == null)
                {
                    return null;
                }
                else if (this.ImageUrls.Length < 20)
                {
                    return this.ImageUrls;
                }
                return this.ImageUrls.Substring(0, 20) + "...";
            }
        }
    }
}
