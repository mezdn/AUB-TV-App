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
    public class EventsController : ControllerBase
    {
        private readonly TVContext _context;

        public EventsController(TVContext context)
        {
            _context = context;
        }

        // GET: api/Events/5
        [HttpGet("{year}/{month}/{day}")]
        public async Task<ActionResult> GetEvent(int year, int month, int day)
        {
            var @event = await _context.Event.FirstOrDefaultAsync(e => (e.Year == year) && (e.Month == month) && (e.Day == day));

            if (@event == null)
            {
                return Ok($"No Event for {day}-{month}-{year}");
            }

            return Ok(@event.Details);
        }
    }
}
