using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Backend.Data;
using Backend.Models;

namespace Backend.API
{
    [Route("api/[controller]")]
    [ApiController]
    public class DisplayObjectsController : ControllerBase
    {
        private readonly TVContext _context;

        public DisplayObjectsController(TVContext context)
        {
            _context = context;
        }

        // GET: api/DisplayObjects
        [HttpGet]
        public async Task<ActionResult> GetDisplayObject()
        {
            var categories = await _context.Category.Select(c => c.Name).ToListAsync();
            var result = new Dictionary<string, List<object>>();

            var items = _context.DisplayObject;

            foreach (var category in categories)
            {
                var catItems = await items.Where(c => c.Category == category).Select(i => new JsonResult( new {
                    type = i.Type.ToString(),
                    title = i.Title?? "",
                    description = i.Description?? "",
                    youtubeId = i.YouTubeID?? "",
                    imageUrls = i.ImageUrls?? ""
                }).Value).ToListAsync();

                result.Add(category, catItems);
            }

            return new JsonResult(result);
        }
    }
}
