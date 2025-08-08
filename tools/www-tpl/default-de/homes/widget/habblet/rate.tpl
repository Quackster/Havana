{% set average = sticker.getAverageRating() %}
{% set px = sticker.getRatingPixels() %}

<script type="text/javascript">	
	var ratingWidget;
	
		ratingWidget = new RatingWidget({{ playerDetails.id }}, {{ sticker.getId() }});
	
</script><div class="rating-average">
		<b>Durchsnitt: {{ average }}</b><br/>
	<div id="rating-stars" class="rating-stars" >
				<ul id="rating-unit_ul1" class="rating-unit-rating">
				<li class="rating-current-rating" style="width:{{ px }}px;" />
	
			</ul>	
	</div>
	{{ sticker.getVoteCount() }} Gesamt
	<br/>
	({{ sticker.getHighVoteCount() }} haben >4 gestimmt)
</div>